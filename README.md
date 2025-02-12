# 🚀 Login & WebView Integration with API and SharedPreferences  

This project implements a **Login System** with API integration, where user credentials and responses are stored locally using **SharedPreferences**. After a successful login, the app navigates to the next activity, which contains a **WebView** displaying a chat application URL. The stored login data is synced to **WebView's local storage** for seamless session management.  

## 📌 Features  
✔️ **API-based Login** – Authenticates users and stores responses.  
✔️ **SharedPreferences Storage** – Saves login data locally.  
✔️ **WebView Chat Integration** – Loads a chat app inside WebView.  
✔️ **Local Storage Sync** – Injects login data into WebView’s local storage.  
✔️ **Seamless User Experience** – No need to log in again inside WebView.  

## 🛠️ Tech Stack  
🔹 **Android (Java/Kotlin)**  
🔹 **API Integration**  
🔹 **SharedPreferences**  
🔹 **WebView**  

## 🚀 How It Works  
1️⃣ **User Logs In** → API authenticates the user and saves response in SharedPreferences.  
2️⃣ **Navigation to WebView** → Displays the chat application.  
3️⃣ **Local Storage Sync** → Injects login data into WebView.  
4️⃣ **Seamless Session** → WebView fetches stored data and maintains the session.  

 

## 📜 License  
📝 MIT License (if applicable)  
