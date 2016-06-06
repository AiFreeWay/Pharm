package sample.presentation.utils;


import sample.presentation.controllers.SearchController;
import sample.presentation.models.SearchParams;

public class SearchParamsBuilder {

    public static SearchParams Build(SearchController searchController) {
        String id = escapeQuote(searchController.getIdFieldValue());
        if (id.equals(searchController.POSITION_TITLE))
            id = searchController.EMPTY_LINE;

        String title = escapeQuote(searchController.getTitleFieldValue());
        if (title.equals(searchController.TITLE_TITLE))
            title = searchController.EMPTY_LINE;

        String provider = escapeQuote(searchController.getProviderFieldValue());
        if (provider.equals(searchController.PROVIDER_TITLE))
            provider = searchController.EMPTY_LINE;

        return new SearchParams(id, title, provider);
    }

    private static String escapeQuote(String data) {
        return data.replaceAll("\'", "\"");
    }
}
