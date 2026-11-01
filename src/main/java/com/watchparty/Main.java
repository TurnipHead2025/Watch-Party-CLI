package com.watchparty;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        WatchPartyApiClient apiClient =
        new WatchPartyApiClient(HttpClient.newHttpClient());

        System.out.println("1. List productions");
        System.out.println("2. Show a session's current beat");
        System.out.println("3. Show a session's current scene");
        System.out.println("4. Show a beat by ID");
        System.out.print("Choose 1-4: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                try {
                    for (String title : apiClient.getProductions()) {
                        System.out.println(title);
                        System.out.println();
                    }
                } catch (IOException exception) {
                    System.out.println("Could not reach API: " + exception.getMessage());
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    System.out.println("Request was interrupted.");
                }
                break;             
                //Uses session Id to find the beat position within the session
                //The user inputs the session ID, and the CLI calls the API client to get the current beat index.
            case 2:
                System.out.print("Enter session ID: ");
                long sessionId = scanner.nextLong();
                try {
                    int beatIndex = apiClient.getCurrentBeatIndex(sessionId);
                    System.out.println("Current beat position: " + (beatIndex + 1));
                    System.out.println();
                } catch (IOException exception) {
                    System.out.println("Could not reach API: " + exception.getMessage());
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    System.out.println("Request was interrupted.");
                    System.out.println();
                }
                break;
            case 3:
                System.out.print("Enter session ID: ");
                long sceneSessionId = scanner.nextLong();
                try {
                    int sceneIndex = apiClient.getCurrentSceneIndex(sceneSessionId);
                    System.out.println("Current scene position: " + (sceneIndex + 1));
                    System.out.println();
                } catch (IOException exception) {
                    System.out.println("Could not reach API: " + exception.getMessage());
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    System.out.println("Request was interrupted.");
                    System.out.println();
                }
                break;
            case 4:
                System.out.print("Enter beat ID: ");
                long beatId = scanner.nextLong();
                try {
                    System.out.println(apiClient.getBeatContent(beatId));
                } catch (IOException exception) {
                    System.out.println("Could not reach API: " + exception.getMessage());
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    System.out.println("Request was interrupted.");
                }
                break;
            default:
                System.out.println("Invalid choice");
        }
    }
}