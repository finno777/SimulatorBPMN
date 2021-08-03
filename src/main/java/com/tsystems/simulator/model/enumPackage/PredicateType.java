package com.tsystems.simulator.model.enumPackage;

import com.tsystems.simulator.service.PredicateTypeService;

import java.util.Map;

public enum PredicateType implements PredicateTypeService {
    OR {
        @Override
        public boolean execute(Map<String, String> map) {
            return map.entrySet().stream().anyMatch(entry -> entry.getKey().equals(entry.getValue()));
        }
    },
    AND {
        @Override
        public boolean execute(Map<String, String> map) {
            return map.entrySet().stream().allMatch(entry -> entry.getKey().equals(entry.getValue()));
        }
    }
}
