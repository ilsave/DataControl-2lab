
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document document = null;
        try {
            builder = documentBuilderFactory.newDocumentBuilder();
            document = builder.parse("src/main/resources/example.xml");
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xPath = xpathFactory.newXPath();

            println("playersWithRightStrikingLeg");
            List<String> playersWithRightStrikingLeg = getPlayersWithStrikingLeg(document, xPath, "Right");
            playersWithRightStrikingLeg.forEach(Main::println);
            println("");

            println("getPlayersWith Russian region League");
            List<String> list3 = getPlayersWithLeague(document, xPath, "Russian region League");
            list3.forEach(Main::println);
            println("");


            println("getLeaguesWithTeamCount = 5");
            List<String> list4 = getLeaguesWithTeamCount(document, xPath, 5);
            list4.forEach(Main::println);
            println("");



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void println(String line) {
        System.out.println(line);
    }

    private static List<String> allPlayers(Document doc, XPath xPath) {
        List<String> list = new ArrayList<>();
        try {
            XPathExpression xPathExpression = xPath.compile(
                    "/Players/Player/name/text()"
            );
            NodeList nodes = (NodeList) xPathExpression.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++) {
                list.add(nodes.item(i).getNodeValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private static List<String> getPlayersWithStrikingLeg(Document doc, XPath xpath, String strikingLeg) {
        List<String> list = new ArrayList<>();
        try {
            XPathExpression xPathExpression = xpath.compile(
                    "/Players/Player[strikingLeg='" + strikingLeg + "']/name/text()"
            );
            NodeList nodeList = (NodeList) xPathExpression.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                list.add(nodeList.item(i).getNodeValue());
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static List<String> getLeaguesWithTeamCount(Document doc, XPath xpath, int count) {
        List<String> list = new ArrayList<>();
        try {
            XPathExpression xPathExpression = xpath.compile(
                    "/Players/Player[club/league/teamsCount>"+ count + "]/club/league/name/text()"
            );
            NodeList nodeList = (NodeList) xPathExpression.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                list.add(nodeList.item(i).getNodeValue());
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static List<String> getPlayersWithLeague(Document doc, XPath xpath, String leagueName) {
        List<String> list = new ArrayList<>();
        try {
            XPathExpression xPathExpression = xpath.compile(
                    "/Players/Player[club/league/name='" + leagueName + "']/name/text()"
            );
            NodeList nodeList = (NodeList) xPathExpression.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                list.add(nodeList.item(i).getNodeValue());
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return list;
    }
}


