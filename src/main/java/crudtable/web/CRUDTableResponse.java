package crudtable.web;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CRUDTableResponse<T> {	
	private long total;
	private List<T> rows;
	private String message;
	private String error;
}
