package org.model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author erwin
 */
public class DDFormat {

    private final SimpleStringProperty fieldName;
    private final SimpleStringProperty dataType;
    private final SimpleStringProperty length;
    private final SimpleStringProperty alias;
    private final SimpleStringProperty description;

    public DDFormat(String fieldName, String alias, String dataType, String length, String description) {
        this.fieldName = new SimpleStringProperty(fieldName);
        this.dataType = new SimpleStringProperty(dataType);
        this.length = new SimpleStringProperty(length);
        this.alias = new SimpleStringProperty(alias);
        this.description = new SimpleStringProperty(description);
    }

    public DDFormat() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getFieldName() {
        return fieldName.get();
    }

    public void setFieldName(String v) {
        fieldName.set(v);
    }

    public String getDataType() {
        return dataType.get();
    }

    public void setDataType(String v) {
        dataType.set(v);
    }

    public String getLength() {
        return length.get();
    }

    public void setLength(String v) {
        length.set(v);
    }

    public String getAlias() {
        return alias.get();
    }

    public void setAlias(String v) {
        alias.set(v);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String v) {
        description.set(v);
    }
}
