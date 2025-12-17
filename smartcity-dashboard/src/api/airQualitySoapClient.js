import axios from "axios";
import { parseStringPromise } from "xml2js";

const SOAP_URL = "http://localhost:8085/api/airquality/";
const HEADERS = {
   "Content-Type": "text/xml;charset=UTF-8",
   SOAPAction: ""
};

// export async function getAirQuality(zone) {
//    const xml = `
// <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
//                   xmlns:air="http://enicarthage.com/airquality/">
//     <soapenv:Header/>
//     <soapenv:Body>
//         <air:getAirQualityRequest>
//             <air:zone>${zone}</air:zone>
//         </air:getAirQualityRequest>
//     </soapenv:Body>
// </soapenv:Envelope>
// `;

//    const response = await axios.post(
//       SOAP_URL + zone,
//       xml,
//       {
//          headers: HEADERS,
//          responseType: "text" // 🔥 CRITIQUE
//       }
//    );

//    const result = await parseStringPromise(response.data);

//    return result["soapenv:Envelope"]["soapenv:Body"][0]
//    ["ns2:getAirQualityResponse"][0];
// }

export async function getAirQuality(zone) {
   const response = await axios.get(
      `http://localhost:8085/api/airquality/${zone}`
   );
   return response.data;
}