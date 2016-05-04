package services;

import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class SOAPClientSAAJ {
    
    public String executePurchase(String token, String payerID, double numBought) {
        String response = "faces/thankyou.xhtml";
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            
            // Send SOAP Message to SOAP Server
            String url = "https://api-3t.sandbox.paypal.com/2.0/";
            SOAPMessage soapResponse = soapConnection.call(createPurchaseRequest(token, payerID, numBought), url);

            NodeList allNodes = soapResponse.getSOAPBody().getChildNodes();
            
            String a = allNodes.item(0).getChildNodes().item(1).getTextContent();
            
            if (!a.equals("Success")) {
                response = "faces/confirmation.xhtml";
            }

            
//            Iterator iter = soapResponse.getSOAPBody().getChildElements();
//            while (iter.hasNext()) {
//                Node curr = (Node)iter.next();
//                if (curr.getNodeType() == Node.ELEMENT_NODE) {
//                    Element ele = (Element)curr;
//                    if (ele.getNodeName() == "SetExpressCheckoutResponse") {
//                        return ele.getLastChild().getTextContent();
//                    }
//                }
//            }
            // Process the SOAP Response
            printSOAPResponse(soapResponse);

            soapConnection.close();
        } catch (Exception e) {
            System.err.println("Error occurred while sending SOAP Request to Server");
            e.printStackTrace();
        }
        return response;
    }
    
    public String getPurchaseToken(double numBought) {
        String token = "";
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            
            // Send SOAP Message to SOAP Server
            String url = "https://api-3t.sandbox.paypal.com/2.0/";
            SOAPMessage soapResponse = soapConnection.call(createTokenRequest(numBought), url);

            Iterator iter = soapResponse.getSOAPBody().getChildElements();
            while (iter.hasNext()) {
                Node curr = (Node)iter.next();
                if (curr.getNodeType() == Node.ELEMENT_NODE) {
                    Element ele = (Element)curr;
                    if (ele.getNodeName() == "SetExpressCheckoutResponse") {
                        token = ele.getLastChild().getTextContent();
                    }
                }
            }

            soapConnection.close();
        } catch (Exception e) {
            System.err.println("Error occurred while sending SOAP Request to Server");
            e.printStackTrace();
        }
        return token;
    }
    
    public String getPayerID(String token) {
        String payerID = "";
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            
            // Send SOAP Message to SOAP Server
            String url = "https://api-3t.sandbox.paypal.com/2.0/";
            SOAPMessage soapResponse = soapConnection.call(createDetailsRequest(token), url);

            NodeList allNodes = soapResponse.getSOAPBody().getChildNodes();
            
            payerID = allNodes.item(0).getChildNodes().item(5).getChildNodes().item(1).getChildNodes().item(1).getTextContent();

            soapConnection.close();
        } catch (Exception e) {
            System.err.println("Error occurred while sending SOAP Request to Server");
            e.printStackTrace();
        }
        return payerID;
    }
    
    private static SOAPMessage createPurchaseRequest(String token, String payerID, double numBought) throws Exception {
        
        String totCost = "" + (numBought * 0.22);
        
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("ns", "urn:ebay:api:PayPalAPI");
        envelope.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        envelope.addNamespaceDeclaration("SOAP-ENC", "http://schemas.xmlsoap.org/soap/encoding/");
        envelope.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");
        envelope.addNamespaceDeclaration("ebl", "urn:ebay:apis:eBLBaseComponents");
        envelope.setEncodingStyle("http://schemas.xmlsoap.org/soap/encoding/");
   
        // Header
        SOAPHeader header = envelope.getHeader();
        SOAPElement rCreds = header.addChildElement("RequesterCredentials", "ns");
        SOAPElement creds = rCreds.addChildElement("Credentials", "ebl");
        SOAPElement userName = creds.addChildElement("Username", "ebl");
        userName.addTextNode("mrprice-facilitator_api1.ilstu.edu");
        SOAPElement password = creds.addChildElement("Password", "ebl");
        password.addTextNode("YK4AUU5U7AM2UQJF");
        SOAPElement signature = creds.addChildElement("Signature", "ebl");
        signature.addTextNode("AFcWxV21C7fd0v3bYYYRCpSSRl31A4AsOdB4sCjEHXOH2q19E1ot.wJN");

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement setXpressTop = soapBody.addChildElement("DoExpressCheckoutPaymentReq", "ns");
        SOAPElement setXpressMid = setXpressTop.addChildElement("DoExpressCheckoutPaymentRequest");
        
        SOAPElement version = setXpressMid.addChildElement("Version", "ebl");
        version.addTextNode("1.0");
        
        SOAPElement details = setXpressMid.addChildElement("DoExpressCheckoutPaymentRequestDetails", "ebl");
        
        SOAPElement tokenEle = details.addChildElement("Token");
        tokenEle.addTextNode(token);
        
        SOAPElement paymentAction = details.addChildElement("PaymentAction");
        paymentAction.addTextNode("Sale");
        
        SOAPElement payerIDEle = details.addChildElement("PayerID");
        payerIDEle.addTextNode(payerID);
        
        SOAPElement paymentDetails = details.addChildElement("PaymentDetails");
        
        SOAPElement orderTotal = paymentDetails.addChildElement("OrderTotal", "ebl");
        orderTotal.addAttribute(new QName("currencyID"), "USD");
        orderTotal.addTextNode(totCost);
        
        soapMessage.saveChanges();

        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);

        return soapMessage;
    }

    private static SOAPMessage createTokenRequest(double numBought) throws Exception {
        
        String totCost = "" + (numBought * 0.22);
        
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

//        String serverURI = "http://ws.cdyne.com/";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("ns", "urn:ebay:api:PayPalAPI");
        envelope.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        envelope.addNamespaceDeclaration("SOAP-ENC", "http://schemas.xmlsoap.org/soap/encoding/");
        envelope.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");
        envelope.addNamespaceDeclaration("ebl", "urn:ebay:apis:eBLBaseComponents");
        envelope.setEncodingStyle("http://schemas.xmlsoap.org/soap/encoding/");
   
        // Header
        SOAPHeader header = envelope.getHeader();
        SOAPElement rCreds = header.addChildElement("RequesterCredentials", "ns");
        SOAPElement creds = rCreds.addChildElement("Credentials", "ebl");
        SOAPElement userName = creds.addChildElement("Username", "ebl");
        userName.addTextNode("mrprice-facilitator_api1.ilstu.edu");
        SOAPElement password = creds.addChildElement("Password", "ebl");
        password.addTextNode("YK4AUU5U7AM2UQJF");
        SOAPElement signature = creds.addChildElement("Signature", "ebl");
        signature.addTextNode("AFcWxV21C7fd0v3bYYYRCpSSRl31A4AsOdB4sCjEHXOH2q19E1ot.wJN");

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement setXpressTop = soapBody.addChildElement("SetExpressCheckoutReq", "ns");
        SOAPElement setXpressMid = setXpressTop.addChildElement("SetExpressCheckoutRequest", "ns");
        
        SOAPElement version = setXpressMid.addChildElement("Version", "ebl");
        version.addTextNode("98.0");
        
        SOAPElement details = setXpressMid.addChildElement("SetExpressCheckoutRequestDetails", "ebl");
        
        SOAPElement returnURL = details.addChildElement("ReturnURL", "ebl");
        returnURL.addTextNode("http://localhost:8080/IT-353-Group-Project-master/faces/confirmation.xhtml");
        
        SOAPElement cancelURL = details.addChildElement("CancelURL", "ebl");
        cancelURL.addTextNode("http://localhost:8080/IT-353-Group-Project-master/faces/index.xhtml");
        
        // Payment details
        SOAPElement paymentDetails = details.addChildElement("PaymentDetails", "ebl");
        SOAPElement total = paymentDetails.addChildElement("OrderTotal", "ebl");
        total.addAttribute(new QName("currencyID"), "USD");
        total.addTextNode(totCost);
        
        SOAPElement iTotal = paymentDetails.addChildElement("ItemTotal", "ebl");
        iTotal.addAttribute(new QName("currencyID"), "USD");
        iTotal.addTextNode(totCost);
        
        SOAPElement shTotal = paymentDetails.addChildElement("ShippingTotal", "ebl");
        shTotal.addAttribute(new QName("currencyID"), "USD");
        shTotal.addTextNode("0.00");
        
        SOAPElement notifyURL = paymentDetails.addChildElement("NotifyURL", "ebl");
        
        SOAPElement item = paymentDetails.addChildElement("PaymentDetailsItem", "ebl");
        
        SOAPElement itemName = item.addChildElement("Name", "ebl");
        itemName.addTextNode("FMSC Pixel Donation");
        
        SOAPElement itemQuantity = item.addChildElement("Quantity", "ebl");
        itemQuantity.addTextNode(((int)numBought) + "");
        
        SOAPElement itemAmount = item.addChildElement("Amount", "ebl");
        itemAmount.addAttribute(new QName("currencyID"), "USD");
        itemAmount.addTextNode("0.22");
        
        SOAPElement itemDescrip = item.addChildElement("Description", "ebl");
        
        SOAPElement itemCategory = item.addChildElement("ItemCategory", "ebl");
        itemCategory.addTextNode("Physical");
        
        SOAPElement action = paymentDetails.addChildElement("PaymentAction", "ebl");
        action.addTextNode("Sale");
        
//        MimeHeaders headers = soapMessage.getMimeHeaders();
//        headers.addHeader("SOAPAction", serverURI  + "VerifyEmail");

        soapMessage.saveChanges();

        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();

        return soapMessage;
    }
    
    private static SOAPMessage createDetailsRequest(String tokenStr) throws Exception {
        
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

//        String serverURI = "http://ws.cdyne.com/";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("ns", "urn:ebay:api:PayPalAPI");
        envelope.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        envelope.addNamespaceDeclaration("SOAP-ENC", "http://schemas.xmlsoap.org/soap/encoding/");
        envelope.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");
        envelope.addNamespaceDeclaration("ebl", "urn:ebay:apis:eBLBaseComponents");
        envelope.setEncodingStyle("http://schemas.xmlsoap.org/soap/encoding/");
   
        // Header
        SOAPHeader header = envelope.getHeader();
        SOAPElement rCreds = header.addChildElement("RequesterCredentials", "ns");
        SOAPElement creds = rCreds.addChildElement("Credentials", "ebl");
        SOAPElement userName = creds.addChildElement("Username", "ebl");
        userName.addTextNode("mrprice-facilitator_api1.ilstu.edu");
        SOAPElement password = creds.addChildElement("Password", "ebl");
        password.addTextNode("YK4AUU5U7AM2UQJF");
        SOAPElement signature = creds.addChildElement("Signature", "ebl");
        signature.addTextNode("AFcWxV21C7fd0v3bYYYRCpSSRl31A4AsOdB4sCjEHXOH2q19E1ot.wJN");

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement setXpressTop = soapBody.addChildElement("GetExpressCheckoutDetailsReq", "ns");
        SOAPElement setXpressMid = setXpressTop.addChildElement("GetExpressCheckoutDetailsRequest", "ns");
        
        SOAPElement version = setXpressMid.addChildElement("Version", "ebl");
        version.addTextNode("98.0");
        
        SOAPElement token = setXpressMid.addChildElement("Token", "ns");
        token.addTextNode(tokenStr);
        
        soapMessage.saveChanges();

        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();

        return soapMessage;
    }
    
    /**
     * Method used to print the SOAP Response
     */
    private static void printSOAPResponse(SOAPMessage soapResponse) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Source sourceContent = soapResponse.getSOAPPart().getContent();
        System.out.print("\nResponse SOAP Message = ");
        StreamResult result = new StreamResult(System.out);
        transformer.transform(sourceContent, result);
    }
}