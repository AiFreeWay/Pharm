package sample.domain.models;

public class Record {

    private String id;
    private String title;
    private String provider;
    private String certificate;
    private String date;
    private String description;
    private String loadDate;

    public Record() {

    }

    public Record(String id, String title, String provider, String certificate, String date, String description) {
        this.id = id;
        this.title = title;
        this.provider = provider;
        this.certificate = certificate;
        this.date = date;
        this.description = description;
    }

    public Record(String id, String title, String provider, String certificate, String date, String description, String loadDate) {
        this.id = id;
        this.title = title;
        this.provider = provider;
        this.certificate = certificate;
        this.date = date;
        this.description = description;
        this.loadDate = loadDate;
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

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLoadDate() {
        return loadDate;
    }

    public void setLoadDate(String loadDate) {
        this.loadDate = loadDate;
    }

    @Override
    public String toString() {
        return id+" "+title+" "+date;
    }
}
