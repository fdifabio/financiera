package ar.edu.unrn.lia.dto;

public class MonthYearDTO {
    private String anio;
    private String mes;

    public MonthYearDTO() {
    }

    public MonthYearDTO(Integer anio, Integer mes) {
        this.anio = anio.toString();
        this.mes = mes.toString();
    }


    public String getAnio() {
        return anio;
    }

    public String getMes() {
        return mes;
    }
}
