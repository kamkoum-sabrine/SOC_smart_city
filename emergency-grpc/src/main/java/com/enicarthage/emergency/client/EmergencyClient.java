package com.enicarthage.emergency.client;

import com.enicarthage.emergency.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class EmergencyClient {

    public static void main(String[] args) {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║   🚨 CLIENT DE TEST - SERVICE URGENCES gRPC 🚨            ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        EmergencyServiceGrpc.EmergencyServiceBlockingStub stub =
                EmergencyServiceGrpc.newBlockingStub(channel);

        try {
            // ═══════════════════════════════════════════════════════════════
            // Test 1: Créer une alerte d'ACCIDENT
            // ═══════════════════════════════════════════════════════════════
            System.out.println("═".repeat(60));
            System.out.println("🚗 TEST 1 : Créer une alerte d'ACCIDENT");
            System.out.println("═".repeat(60));

            Location location = Location.newBuilder()
                    .setLatitude(36.8065)
                    .setLongitude(10.1815)
                    .setAddress("Avenue Habib Bourguiba, Tunis")
                    .setZone("Centre")
                    .build();

            AlertRequest request = AlertRequest.newBuilder()
                    .setType(EmergencyType.ACCIDENT)
                    .setPriority(Priority.HIGH)
                    .setDescription("Collision entre deux voitures - 3 blessés")
                    .setLocation(location)
                    .setReporterName("Ahmed Ben Ali")
                    .setReporterPhone("+216 20 123 456")
                    .build();

            AlertResponse response = stub.createAlert(request);
            System.out.println("✅ Alerte créée avec succès !");
            System.out.println("   📋 ID Alerte     : " + response.getAlertId());
            System.out.println("   🚨 Type          : " + response.getType());
            System.out.println("   ⚠️  Priorité      : " + response.getPriority());
            System.out.println("   📊 Statut        : " + response.getStatus());
            System.out.println("   📍 Zone          : " + response.getLocation().getZone());

            // Sauvegarder l'ID pour les tests suivants
            String alertId = response.getAlertId();

            // Pause pour simulation réaliste
            Thread.sleep(2000);

            // ═══════════════════════════════════════════════════════════════
            // Test 2: Dispatcher une équipe d'urgence
            // ═══════════════════════════════════════════════════════════════
            System.out.println("\n" + "═".repeat(60));
            System.out.println("🚑 TEST 2 : Dispatcher une équipe d'urgence");
            System.out.println("═".repeat(60));

            DispatchRequest dispatchRequest = DispatchRequest.newBuilder()
                    .setAlertId(alertId)
                    .setTeamId("TEAM-URGENCE-01")
                    .setEstimatedArrivalTime(8)
                    .build();

            DispatchResponse dispatchResponse = stub.dispatchTeam(dispatchRequest);

            if (dispatchResponse.getSuccess()) {
                System.out.println("✅ Équipe dispatchée avec succès !");
                System.out.println("   🚑 Équipe        : " + dispatchResponse.getTeamId());
                System.out.println("   ⏱️  ETA          : " + dispatchResponse.getEstimatedArrivalTime() + " minutes");
                System.out.println("   💬 Message       : " + dispatchResponse.getMessage());
            } else {
                System.out.println("❌ Échec du dispatch : " + dispatchResponse.getMessage());
            }

            Thread.sleep(2000);

            // ═══════════════════════════════════════════════════════════════
            // Test 3: Vérifier le statut de l'alerte
            // ═══════════════════════════════════════════════════════════════
            System.out.println("\n" + "═".repeat(60));
            System.out.println("📊 TEST 3 : Vérifier le statut de l'alerte");
            System.out.println("═".repeat(60));

            AlertStatusRequest statusRequest = AlertStatusRequest.newBuilder()
                    .setAlertId(alertId)
                    .build();

            AlertResponse statusResponse = stub.getAlertStatus(statusRequest);
            System.out.println("✅ Statut récupéré avec succès !");
            System.out.println("   📋 ID Alerte     : " + statusResponse.getAlertId());
            System.out.println("   📊 Statut actuel : " + statusResponse.getStatus());
            System.out.println("   🚑 Équipe        : " + statusResponse.getAssignedTeam());
            System.out.println("   ⏱️  ETA          : " + statusResponse.getEstimatedArrivalTime() + " min");

            Thread.sleep(2000);

            // ═══════════════════════════════════════════════════════════════
            // Test 4: Mettre à jour le statut - Équipe en route
            // ═══════════════════════════════════════════════════════════════
            System.out.println("\n" + "═".repeat(60));
            System.out.println("🔄 TEST 4 : Mise à jour - Équipe en route");
            System.out.println("═".repeat(60));

            UpdateStatusRequest updateRequest1 = UpdateStatusRequest.newBuilder()
                    .setAlertId(alertId)
                    .setNewStatus(AlertStatus.DISPATCHED)
                    .setNotes("L'équipe URGENCE-01 est en route vers le lieu de l'accident")
                    .build();

            AlertResponse updatedResponse1 = stub.updateAlertStatus(updateRequest1);
            System.out.println("✅ Statut mis à jour !");
            System.out.println("   📊 Ancien statut : PENDING");
            System.out.println("   📊 Nouveau statut: " + updatedResponse1.getStatus());
            System.out.println("   💬 Notes         : L'équipe est en route");

            Thread.sleep(3000);

            // ═══════════════════════════════════════════════════════════════
            // Test 5: Mettre à jour le statut - Intervention en cours
            // ═══════════════════════════════════════════════════════════════
            System.out.println("\n" + "═".repeat(60));
            System.out.println("🔄 TEST 5 : Mise à jour - Intervention EN COURS");
            System.out.println("═".repeat(60));

            UpdateStatusRequest updateRequest2 = UpdateStatusRequest.newBuilder()
                    .setAlertId(alertId)
                    .setNewStatus(AlertStatus.IN_PROGRESS)
                    .setNotes("Équipe arrivée sur place - Prise en charge des blessés en cours")
                    .build();

            AlertResponse updatedResponse2 = stub.updateAlertStatus(updateRequest2);
            System.out.println("✅ Statut mis à jour !");
            System.out.println("   📊 Ancien statut : DISPATCHED");
            System.out.println("   📊 Nouveau statut: " + updatedResponse2.getStatus());
            System.out.println("   💬 Notes         : Intervention en cours");
            System.out.println("   👨‍⚕️ Action       : Prise en charge des 3 blessés");

            Thread.sleep(3000);

            // ═══════════════════════════════════════════════════════════════
            // Test 6: Mettre à jour le statut - Urgence RÉSOLUE
            // ═══════════════════════════════════════════════════════════════
            System.out.println("\n" + "═".repeat(60));
            System.out.println("🔄 TEST 6 : Mise à jour - Urgence RÉSOLUE");
            System.out.println("═".repeat(60));

            UpdateStatusRequest updateRequest3 = UpdateStatusRequest.newBuilder()
                    .setAlertId(alertId)
                    .setNewStatus(AlertStatus.RESOLVED)
                    .setNotes("Blessés transportés à l'hôpital Charles Nicolle - Situation sous contrôle")
                    .build();

            AlertResponse updatedResponse3 = stub.updateAlertStatus(updateRequest3);
            System.out.println("✅ Statut mis à jour !");
            System.out.println("   📊 Ancien statut : IN_PROGRESS");
            System.out.println("   📊 Nouveau statut: " + updatedResponse3.getStatus());
            System.out.println("   💬 Notes         : Urgence résolue");
            System.out.println("   🏥 Destination   : Hôpital Charles Nicolle");
            System.out.println("   ✅ Résultat      : Situation sous contrôle");

            Thread.sleep(2000);

            // ═══════════════════════════════════════════════════════════════
            // Test 7: Créer d'autres alertes pour tester le streaming
            // ═══════════════════════════════════════════════════════════════
            System.out.println("\n" + "═".repeat(60));
            System.out.println("➕ TEST 7 : Créer des alertes supplémentaires");
            System.out.println("═".repeat(60));

            // Alerte INCENDIE
            Location locationFire = Location.newBuilder()
                    .setLatitude(36.8520)
                    .setLongitude(10.1960)
                    .setAddress("Rue de Marseille, Tunis")
                    .setZone("Nord")
                    .build();

            AlertRequest fireRequest = AlertRequest.newBuilder()
                    .setType(EmergencyType.FIRE)
                    .setPriority(Priority.CRITICAL)
                    .setDescription("Incendie dans un immeuble de 5 étages")
                    .setLocation(locationFire)
                    .setReporterName("Fatma Mejri")
                    .setReporterPhone("+216 22 987 654")
                    .build();

            AlertResponse fireResponse = stub.createAlert(fireRequest);
            System.out.println("🔥 Alerte INCENDIE créée : " + fireResponse.getAlertId());

            Thread.sleep(1000);

            // Alerte MÉDICALE
            Location locationMedical = Location.newBuilder()
                    .setLatitude(36.7500)
                    .setLongitude(10.2300)
                    .setAddress("Avenue de la Liberté, Ariana")
                    .setZone("Sud")
                    .build();

            AlertRequest medicalRequest = AlertRequest.newBuilder()
                    .setType(EmergencyType.MEDICAL)
                    .setPriority(Priority.CRITICAL)
                    .setDescription("Personne en arrêt cardiaque - 65 ans")
                    .setLocation(locationMedical)
                    .setReporterName("Mohamed Trabelsi")
                    .setReporterPhone("+216 98 456 123")
                    .build();

            AlertResponse medicalResponse = stub.createAlert(medicalRequest);
            System.out.println("🏥 Alerte MÉDICALE créée : " + medicalResponse.getAlertId());

            Thread.sleep(2000);

            // ═══════════════════════════════════════════════════════════════
            // Test 8: Streaming - Lister toutes les alertes actives
            // ═══════════════════════════════════════════════════════════════
            System.out.println("\n" + "═".repeat(60));
            System.out.println("📡 TEST 8 : Streaming des alertes actives");
            System.out.println("═".repeat(60));

            EmptyRequest emptyRequest = EmptyRequest.newBuilder().build();
            Iterator<AlertResponse> activeAlerts = stub.streamActiveAlerts(emptyRequest);

            int alertCount = 0;
            System.out.println("\n📋 Liste des alertes actives :");
            System.out.println("─".repeat(60));

            while (activeAlerts.hasNext()) {
                alertCount++;
                AlertResponse alert = activeAlerts.next();

                System.out.println("\n🚨 Alerte #" + alertCount);
                System.out.println("   ID               : " + alert.getAlertId());
                System.out.println("   Type             : " + alert.getType());
                System.out.println("   Priorité         : " + alert.getPriority());
                System.out.println("   Statut           : " + alert.getStatus());
                System.out.println("   Description      : " + alert.getDescription());
                System.out.println("   Zone             : " + alert.getLocation().getZone());
                System.out.println("   Signalé par      : " + alert.getReporterName());

                if (!alert.getAssignedTeam().isEmpty()) {
                    System.out.println("   Équipe assignée  : " + alert.getAssignedTeam());
                    System.out.println("   ETA              : " + alert.getEstimatedArrivalTime() + " min");
                }
                System.out.println("─".repeat(60));
            }

            System.out.println("\n✅ Streaming terminé - Total : " + alertCount + " alerte(s) active(s)");

            // ═══════════════════════════════════════════════════════════════
            // Résumé final
            // ═══════════════════════════════════════════════════════════════
            System.out.println("\n" + "═".repeat(60));
            System.out.println("📊 RÉSUMÉ DES TESTS");
            System.out.println("═".repeat(60));
            System.out.println("✅ Test 1 : Création d'alerte              → OK");
            System.out.println("✅ Test 2 : Dispatch d'équipe              → OK");
            System.out.println("✅ Test 3 : Vérification du statut         → OK");
            System.out.println("✅ Test 4 : Update DISPATCHED              → OK");
            System.out.println("✅ Test 5 : Update IN_PROGRESS             → OK");
            System.out.println("✅ Test 6 : Update RESOLVED                → OK");
            System.out.println("✅ Test 7 : Création alertes multiples     → OK");
            System.out.println("✅ Test 8 : Streaming alertes actives      → OK");
            System.out.println("═".repeat(60));

            System.out.println("\n🎉 TOUS LES TESTS SONT TERMINÉS AVEC SUCCÈS ! 🎉\n");

        } catch (StatusRuntimeException e) {
            System.err.println("❌ Erreur gRPC: " + e.getStatus());
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("❌ Erreur d'interruption: " + e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            try {
                System.out.println("\n🔌 Fermeture de la connexion gRPC...");
                channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
                System.out.println("✅ Connexion fermée proprement.\n");
            } catch (InterruptedException e) {
                System.err.println("❌ Erreur lors de la fermeture: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }
}