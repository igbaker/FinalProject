package com.example.finalproject.rss;

import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class RssSax {
    private String url;

    public RssSax(String url) {
        this.url = url;
    }

    public List<RssItem> getItems() throws Exception {
        //handler will do parsing with SaxParser
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        RssHandler rssHandler = new RssHandler();
        saxParser.parse(url, rssHandler);
        return rssHandler.getRssItemList();
    }
}
