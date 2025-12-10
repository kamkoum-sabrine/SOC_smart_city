import { EmergencyServiceClient } from "./generated/emergency_grpc_web_pb";
import { EmergencyAlert } from "./generated/emergency_pb";

const client = new EmergencyServiceClient("http://localhost:8085");

export const createAlert = async (data) => {
    return new Promise((resolve, reject) => {
        const alert = new EmergencyAlert();
        alert.setType(data.type);
        alert.setPriority(data.priority);
        alert.setDescription(data.description);
        alert.setLatitude(data.latitude);
        alert.setLongitude(data.longitude);
        alert.setReportername(data.reporterName);
        alert.setReporterphone(data.reporterPhone);

        client.createAlert(alert, {}, (err, response) => {
            if (err) reject(err);
            else resolve({
                alertId: response.getId(),
                type: response.getType(),
                priority: response.getPriority()
            });
        });
    });
};
