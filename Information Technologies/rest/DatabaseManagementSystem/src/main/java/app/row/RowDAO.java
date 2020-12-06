package app.row;

import app.table.Table;
import app.util.BasicDAO;
import app.util.MutationResult;

import java.util.Collection;

public class RowDAO implements BasicDAO<String, Row> {
    Table table;

    public RowDAO(Table table) {
        this.table = table;
    }

    @Override
    public Collection<Row> list() {
        return table.listRows();
    }

    @Override
    public Row get(String key) {
        int index = Integer.parseInt(key);
        return table.getRow(index);
    }

    @Override
    public MutationResult add(Row row) {
        return table.addRow(row);
    }

    @Override
    public MutationResult delete(String key) {
        int index = Integer.parseInt(key);
        return table.deleteRow(index);
    }
}
