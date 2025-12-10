// src/api/airQualitySoapClient.js
import soapRequest from "easy-soap-request";
import { parseStringPromise } from "xml2js";

const SOAP_URL = "http://localhost:8085/api/airquality";
const HEADERS = { "Content-Type": "text/xml;charset=UTF-8", SOAPAction: "" };

export async function getAirQuality(zone) {
    const xml = `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:air="http://enicarthage.com/airquality">
    <soapenv:Header/>
    <soapenv:Body>
       <air:getAirQualityRequest>
          <air:zone>${zone}</air:zone>
       </air:getAirQualityRequest>
    </soapenv:Body>
  </soapenv:Envelope>`;

    const { response } = await soapRequest({ url: SOAP_URL, headers: HEADERS, xml });
    const result = await parseStringPromise(response.body);
    return result["soap:Envelope"]["soap:Body"][0]["ns2:getAirQualityResponse"][0];
}
