package sample.presentation.models;


public class SearchParams {

    private String id;
    private String title;
    private String provider;
    private String dateFrom;
    private String dateTo;

    public SearchParams() {
    }

    public SearchParams(String id, String title, String provider, String dateFrom, String dateTo) {
        this.id = id;
        this.title = title;
        this.provider = provider;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }
}
