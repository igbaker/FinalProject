package com.example.finalproject.rss;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class RssHandler extends DefaultHandler {
    private List<RssItem> RssItemList;
    private RssItem item;
    private boolean parseTitle;
    private boolean parseLink;
    private boolean parseDescription;
    private boolean parseDate;

    //creates arraylist that holds rssitems
    public RssHandler() {
        RssItemList = new ArrayList<RssItem>();
    }

    public List<RssItem> getRssItemList() {
        return RssItemList;
    }

    //called at start of tag
    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        if (name.equals("item"))
            item = new RssItem();
        else if (name.equals("title"))
            parseTitle = true;
        else if (name.equals("link"))
            parseLink = true;
        else if (name.equals("description"))
            parseDescription = true;
        else if (name.equals("date"))
            parseDate = true;
    }

    //called at end of tag
    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {
        if (name.equals("item")) {
            RssItemList.add(item);
            item = null;
        } else if (name.equals("title"))
            parseTitle = false;
        else if (name.equals("link"))
            parseLink = false;
        else if (name.equals("description"))
            parseDescription = false;
        else if (name.equals("date"))
            parseDate = false;
    }

    //parses each character in tag
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (item != null) {
            if (parseTitle)
                item.setTitle(new String(ch, start, length));
            else if (parseLink)
                item.setLink(new String(ch, start, length));
            else if (parseDescription)
                item.setDescription(new String(ch, start, length));
            else if (parseDate)
                item.setDate(new String(ch, start, length));
        }
    }
}


