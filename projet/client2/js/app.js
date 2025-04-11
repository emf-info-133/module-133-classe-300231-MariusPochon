

// Fonction pour gérer la soumission du formulaire de connexion
document.getElementById("loginForm").addEventListener("submit", function(e) {
    e.preventDefault();
    
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const resultEl = document.getElementById("loginResult");
    const API_BASE_URL = 'http://localhost:8080/client/login';

  
    const userCredentials = {
      username: username,
      password: password
    };
  
    const xhr = new XMLHttpRequest();
    xhr.open("POST", API_BASE_URL, true);
    xhr.withCredentials = true; // Important pour les cookies de session
    xhr.setRequestHeader("Content-Type", "application/json");
  
    xhr.onload = function() {
      if (xhr.status === 200) {
        try {
          const userData = JSON.parse(xhr.responseText);
          resultEl.textContent = "Bienvenue " + userData.username;
          
          // Redirection basée sur le rôle
          if (userData.role === "admin") {
            window.location.href = "../../../client2/index.html"; // Page admin
          } else {
            window.location.href = "main.html"; // Page utilisateur standard
          }
        } catch (e) {
          console.error("Erreur lors du traitement des données:", e);
          resultEl.textContent = "Erreur de traitement des données";
        }
      } else {
        resultEl.textContent = "Erreur de connexion";
        console.error("Erreur:", xhr.status, xhr.responseText);
      }
    };
  
    xhr.onerror = function() {
      resultEl.textContent = "Erreur réseau";
      console.error("Erreur réseau");
    };
  
    xhr.send(JSON.stringify(userCredentials));
  });
  
  // Fonction pour gérer la déconnexion
  function logout() {
    const xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8080/client/logout", true);
    xhr.withCredentials = true;
    xhr.onload = function() {
      if (xhr.status === 200) {
        window.location.href = "./index.html";
      } else {
        console.error("Erreur lors de la déconnexion:", xhr.status);
      }
    };
    xhr.onerror = function() {
      console.error("Erreur réseau lors de la déconnexion");
    };
    xhr.send();
  }