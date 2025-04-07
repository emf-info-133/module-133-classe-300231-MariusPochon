const API_BASE_URL = 'http://localhost:8080/api/auth';
const loginForm = document.getElementById('loginForm');
const messageDiv = document.getElementById('message');
const profileInfo = document.getElementById('profileInfo');
const logoutBtn = document.getElementById('logoutBtn');

// Gestion de la soumission du formulaire
loginForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    
    try {
        const response = await fetch(`${API_BASE_URL}/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password })
        });
        
        const result = await response.text();
        
        if (response.ok) {
            showMessage(result, 'success');
            await fetchUserProfile();
        } else {
            showMessage(result, 'error');
        }
    } catch (error) {
        showMessage('Erreur de connexion au serveur', 'error');
        console.error('Error:', error);
    }
});

// Récupération du profil utilisateur
async function fetchUserProfile() {
    try {
        const response = await fetch(`${API_BASE_URL}/profile`, {
            credentials: 'include' // Important pour les cookies de session
        });
        
        if (response.ok) {
            const text = await response.text();
            // Pour un vrai projet, tu devrais retourner du JSON depuis ton backend
            // Ici on affiche simplement la réponse textuelle
            document.getElementById('profileId').textContent = 'ID récupéré';
            document.getElementById('profileUsername').textContent = document.getElementById('username').value;
            document.getElementById('profileRole').textContent = 'Rôle récupéré';
            
            profileInfo.style.display = 'block';
            loginForm.style.display = 'none';
        } else {
            showMessage('Erreur lors de la récupération du profil', 'error');
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

// Gestion de la déconnexion
if (logoutBtn) {
    logoutBtn.addEventListener('click', async () => {
        try {
            const response = await fetch(`${API_BASE_URL}/logout`, {
                method: 'POST',
                credentials: 'include'
            });
            
            if (response.ok) {
                showMessage('Déconnecté avec succès', 'success');
                profileInfo.style.display = 'none';
                loginForm.style.display = 'flex';
                loginForm.reset();
            }
        } catch (error) {
            console.error('Error:', error);
        }
    });
}

// Affichage des messages
function showMessage(text, type) {
    messageDiv.textContent = text;
    messageDiv.className = `message ${type}`;
    messageDiv.style.display = 'block';
    
    setTimeout(() => {
        messageDiv.style.display = 'none';
    }, 5000);
}

// Vérification de la connexion au chargement de la page
window.addEventListener('load', async () => {
    await fetchUserProfile();
});