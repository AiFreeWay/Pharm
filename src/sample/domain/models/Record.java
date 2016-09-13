package sample.domain.models;

public class Record {

    private int id;
    private int series_hash;
    private String series;
    private String title;
    private String provider;
    private String certificate;
    private String date;
    private String description;
    private String loadDate;

    public Record() {

    }

    public Record(int series_hash, String series, String title, String provider, String certificate, String date, String description) {
        this.series_hash = series_hash;
        this.series = series;
        this.title = title;
        this.provider = provider;
        this.certificate = certificate;
        this.date = date;
        this.description = description;
    }

    public Record(int id, int series_hash, String series, String title, String provider, String certificate, String date, String description) {
        this.id = id;
        this.series_hash = series_hash;
        this.series = series;
        this.title = title;
        this.provider = provider;
        this.certificate = certificate;
        this.date = date;
        this.description = description;
    }

    public Record(int id, int series_hash, String series, String title, String provider, String certificate, String date, String description, String loadDate) {
        this.id = id;
        this.series_hash = series_hash;
        this.series = series;
        this.title = title;
        this.provider = provider;
        this.certificate = certificate;
        this.date = date;
        this.description = description;
        this.loadDate = loadDate;
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public String toString() {
        return id+" "+title+" "+date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeriesHash() {
        return series_hash;
    }

    public void setSeriesHash(int series_hash) {
        this.series_hash = series_hash;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
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
}
