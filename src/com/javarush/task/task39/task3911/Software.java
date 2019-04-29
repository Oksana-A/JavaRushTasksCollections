package com.javarush.task.task39.task3911;

import java.util.*;
import java.util.stream.Collectors;

public class Software {
    int currentVersion;

    private Map<Integer, String> versionHistoryMap = new LinkedHashMap<>();

    public void addNewVersion(int version, String description) {
        if (version > currentVersion) {
            versionHistoryMap.put(version, description);
            currentVersion = version;
        }
    }

    public int getCurrentVersion() {
        return currentVersion;
    }

    public Map<Integer, String> getVersionHistoryMap() {
        return Collections.unmodifiableMap(versionHistoryMap);
    }

    public boolean rollback(int rollbackVersion) {
        boolean flag = false;
        if (rollbackVersion < currentVersion) {
            if (versionHistoryMap.keySet().contains(rollbackVersion)) {
                flag = true;
                currentVersion = rollbackVersion;
                Set<Integer> set = versionHistoryMap.keySet().stream()
                        .filter(x -> x > rollbackVersion).collect(Collectors.toSet());
                for (Integer i : set) {
                    versionHistoryMap.remove(i);
                }
            }
        }
        return flag;
    }
}
