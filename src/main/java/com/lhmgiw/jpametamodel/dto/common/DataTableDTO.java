package com.lhmgiw.jpametamodel.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DataTableDTO<T> {
    private Long totalRecords;
    private Long filteredRecords;
    private List<T> records;
    private Integer draw;
}
