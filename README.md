Login & WebView Integration with API and SharedPreferences
This project implements a login system using API integration, where user credentials and responses are stored locally using SharedPreferences. After successful login, the app navigates to the next activity, which contains a WebView displaying a chat application URL. The stored login data is synced to WebView's local storage to maintain session persistence.

📌 Features
✅ API-based Login – Authenticates users and saves responses.
✅ SharedPreferences Storage – Stores login data locally.
✅ WebView Chat Integration – Loads a chat app inside WebView.
✅ Local Storage Sync – Transfers login data to WebView for a seamless experience.

🛠️ Tech Stack
Android (Java/Kotlin)
API Integration
SharedPreferences
WebView
🚀 How It Works
User Logs In → API authenticates the user and saves response in SharedPreferences.
Navigation to WebView → Displays the chat application.
Local Storage Sync → Login data is injected into WebView’s local storage.
Seamless Session → WebView fetches login data and maintains the session.

📜 License
MIT License (if applicable)

