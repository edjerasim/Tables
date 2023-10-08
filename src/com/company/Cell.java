package com.company;

public class Cell {
    private Object value; // Стойност на клетката (може да бъде цяло число, дробно число или текст)
    private CellType type; // Тип на клетката (цяло число, дробно число, текст и други)

    public Cell(Object value, CellType type) {
        this.value = value;
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public CellType getType() {
        return type;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setType(CellType type) {
        this.type = type;
    }
}


