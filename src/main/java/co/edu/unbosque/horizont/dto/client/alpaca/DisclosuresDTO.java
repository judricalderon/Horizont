package co.edu.unbosque.horizont.dto.client.alpaca;

public class DisclosuresDTO {
    private boolean is_control_person;
    private boolean is_affiliated_exchange_or_finra;
    private boolean is_politically_exposed;
    private boolean immediate_family_exposed;

    public boolean isIs_control_person() {
        return is_control_person;
    }

    public void setIs_control_person(boolean is_control_person) {
        this.is_control_person = is_control_person;
    }

    public boolean isIs_affiliated_exchange_or_finra() {
        return is_affiliated_exchange_or_finra;
    }

    public void setIs_affiliated_exchange_or_finra(boolean is_affiliated_exchange_or_finra) {
        this.is_affiliated_exchange_or_finra = is_affiliated_exchange_or_finra;
    }

    public boolean isIs_politically_exposed() {
        return is_politically_exposed;
    }

    public void setIs_politically_exposed(boolean is_politically_exposed) {
        this.is_politically_exposed = is_politically_exposed;
    }

    public boolean isImmediate_family_exposed() {
        return immediate_family_exposed;
    }

    public void setImmediate_family_exposed(boolean immediate_family_exposed) {
        this.immediate_family_exposed = immediate_family_exposed;
    }
}
