public class ATag {
    private String text;
    private String href;

    public ATag(String text, String href) {
        this.text = text;
        this.href = href;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String toHtmlString() {
        //<a href="https://www.dr.dk/">Her er et link til danmarks radio</a>
        return "<A href=" + '"' + getHref() + '"' + ">" + getText() + "</a>";
    }
}


