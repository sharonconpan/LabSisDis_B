package util;
public record ComboItem(int id, String name) {
    @Override public String toString(){ return name; }
}
