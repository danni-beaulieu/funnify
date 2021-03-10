package com.funnerimage.funnify.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OperationsInput {
    public List<OperationInput> getOperations() {
        return operations;
    }

    public void setOperations(List<OperationInput> operations) {
        this.operations = operations;
    }

    private List<OperationInput> operations = new ArrayList();

    @SuppressWarnings("unchecked")
    @JsonProperty("operations")
    private void unpackNested(Map<String, Object>[] operationsJson) {
        List<OperationInput> operations = new ArrayList<>();

        for (Map<String, Object> operationJson : operationsJson) {
            OperationInput operation = new OperationInput();

            operation.setType((String) operationJson.get("type"));
            operation.setValue((String) operationJson.getOrDefault("value", null));
            operations.add(operation);
        }
        this.setOperations(operations);
    }
}
