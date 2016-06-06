package sample.presentation.models;


public class SearchParams {

    private String id;
    private String title;
    private String provider;

    public SearchParams() {
    }

    public SearchParams(String id, String title, String provider) {
        this.id = id;
        this.title = title;
        this.provider = provider;
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
}
