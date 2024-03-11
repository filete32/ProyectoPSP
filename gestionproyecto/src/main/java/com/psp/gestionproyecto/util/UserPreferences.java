package com.psp.gestionproyecto.util;

import java.util.prefs.Preferences;

public class UserPreferences {

    private Preferences prefs;

    public UserPreferences() {
        // This will define a node in which the preferences can be stored
        prefs = Preferences.userRoot().node(this.getClass().getName());
    }

    public void saveUsername(String username) {
        prefs.put("USERNAME", username);
    }

    public String getUsername() {
        return prefs.get("USERNAME", "defaultUsername");
    }
}

