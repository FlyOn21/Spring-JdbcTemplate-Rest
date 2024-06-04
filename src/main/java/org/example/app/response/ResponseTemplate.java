package org.example.app.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTemplate<T> {
    private boolean success;
    private String msg;
    private T data;
    private List<String> errors;
}
