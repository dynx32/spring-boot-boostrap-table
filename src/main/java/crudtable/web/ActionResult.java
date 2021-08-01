package crudtable.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionResult {
	private boolean success;
	private String message;
	private Object data;
}
