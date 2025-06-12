
package model;

import java.time.LocalDate;

public record Proyecto(int id, String nombre, LocalDate fechaInicio, LocalDate fechaTermino) {}
