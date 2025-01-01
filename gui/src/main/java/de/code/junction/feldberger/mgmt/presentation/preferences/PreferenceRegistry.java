package de.code.junction.feldberger.mgmt.presentation.preferences;

import de.code.junction.feldberger.mgmt.data.access.preference.Preference;
import de.code.junction.feldberger.mgmt.data.access.preference.PreferenceDataAccessObject;
import de.code.junction.feldberger.mgmt.data.access.preference.PreferenceId;
import de.code.junction.feldberger.mgmt.data.access.user.User;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class PreferenceRegistry {

    private final PreferenceDataAccessObject preferenceDao;
    private final User userReference;
    private final Map<UIPreference, PreferenceId> preferenceKeyMap;
    private final Map<PreferenceId, String> preferenceValueMap;

    public PreferenceRegistry(PreferenceDataAccessObject preferenceDao, User userReference) {

        this.preferenceDao = preferenceDao;
        this.userReference = userReference;

        this.preferenceKeyMap = new HashMap<>();
        this.preferenceValueMap = new HashMap<>();
    }

    private PreferenceId getPreferenceId(UIPreference uiPreference) {

        return preferenceKeyMap.computeIfAbsent(
                uiPreference,
                _uiPreference -> new PreferenceId(
                        userReference,
                        _uiPreference.scope(),
                        _uiPreference.name()
                )
        );
    }

    /**
     * Retrieve preference value as string from db.
     *
     * @param uiPreference ui preference metadata
     * @return preference value
     */
    private String getStringValue(UIPreference uiPreference) {

        final var id = getPreferenceId(uiPreference);

        return preferenceValueMap.computeIfAbsent(
                id,
                _id -> preferenceDao.findById(_id)
                        .map(Preference::getPreferredValue)
                        .orElseGet(() -> {
                            final var stringValue = String.valueOf(uiPreference.defaultValue());
                            preferenceDao.persistPreference(new Preference(_id, stringValue));
                            return stringValue;
                        })
        );
    }

    /**
     * Retrieve parsed preference value.
     *
     * @param uiPreference ui preference metadata
     * @return preference value as specified by {@link UIPreference#type()}
     */
    @SuppressWarnings("unchecked")
    public <T> T getValue(UIPreference uiPreference) {

        final var stringValue = getStringValue(uiPreference);

        if (stringValue.equals("null")) return null;

        final Function<String, Object> parser = switch (uiPreference.type()) {
            case Class<?> c when c == String.class -> value -> value;
            case Class<?> c when c == Integer.class -> Integer::valueOf;
            case Class<?> c when c == Double.class -> Double::valueOf;
            case Class<?> c when c == Boolean.class -> Boolean::valueOf;
            default -> throw new UnsupportedOperationException("Cannot parse value of type " +
                    uiPreference.type().getName());
        };

        return (T) parser.apply(stringValue);
    }

    public <T> void setValue(UIPreference uiPreference, T value) {

        final var id = getPreferenceId(uiPreference);
        final var optionalPreference = preferenceDao.findById(id);

        final var stringValue = String.valueOf(value);

        final var preference = optionalPreference.map(_preference -> {
            _preference.setPreferredValue(stringValue);
            return _preference;
        }).orElse(new Preference(id, stringValue));

        preferenceDao.persistPreference(preference);
        preferenceValueMap.put(id, stringValue);
    }

    public void dropScopedPreferences(String scope) {

        final var preferences = preferenceKeyMap.keySet().stream()
                .filter(preference -> preference.scope().equals(scope))
                .toList();

        final var preferenceIds = preferences.stream()
                .map(preferenceKeyMap::get)
                .toList();

        preferences.forEach(preferenceKeyMap::remove);
        preferenceIds.forEach(preferenceValueMap::remove);

        preferenceDao.deleteForUserAndScope(userReference, scope);
    }
}
