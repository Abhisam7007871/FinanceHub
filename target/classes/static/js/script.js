document.addEventListener('DOMContentLoaded', function() {
    const savedTheme = localStorage.getItem('theme') || 'light';
    setTheme(savedTheme);
    
    const themeOptions = document.querySelectorAll('.theme-option');
    themeOptions.forEach(option => {
        if (option.dataset.theme === savedTheme) {
            option.classList.add('active');
        }
        
        option.addEventListener('click', function() {
            const theme = this.dataset.theme;
            setTheme(theme);
            
            themeOptions.forEach(opt => opt.classList.remove('active'));
            this.classList.add('active');
            
            localStorage.setItem('theme', theme);
        });
    });
    
    function setTheme(theme) {
        if (theme === 'system') {
            if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches) {
                document.body.classList.remove('light-mode');
                document.body.classList.add('dark-mode');
            } else {
                document.body.classList.remove('dark-mode');
                document.body.classList.add('light-mode');
            }
        } else {
            document.body.classList.remove('light-mode', 'dark-mode');
            document.body.classList.add(theme + '-mode');
        }
    }
    
    window.matchMedia('(prefers-color-scheme: dark)').addEventListener('change', e => {
        if (localStorage.getItem('theme') === 'system') {
            setTheme('system');
        }
    });
    
    const profileInput = document.getElementById('profilePictureInput');
    const profilePreview = document.getElementById('profilePreview');
    const profileAvatar = document.getElementById('profileAvatar');
    const uploadBtn = document.getElementById('uploadPictureBtn');
    const removeBtn = document.getElementById('removePictureBtn');
    
    if (uploadBtn && profileInput) {
        uploadBtn.addEventListener('click', () => profileInput.click());
    }
    
    if (profileInput) {
        profileInput.addEventListener('change', function() {
            if (this.files && this.files[0]) {
                const reader = new FileReader();
                
                reader.onload = function(e) {
                    if (profilePreview) {
                        profilePreview.style.backgroundImage = `url(${e.target.result})`;
                        profilePreview.textContent = '';
                    }
                    
                    if (profileAvatar) {
                        profileAvatar.style.backgroundImage = `url(${e.target.result})`;
                        profileAvatar.textContent = '';
                    }
                    
                    localStorage.setItem('profilePicture', e.target.result);
                }
                
                reader.readAsDataURL(this.files[0]);
            }
        });
    }
    
    if (removeBtn) {
        removeBtn.addEventListener('click', function() {
            if (profilePreview) {
                profilePreview.style.backgroundImage = '';
                profilePreview.textContent = 'AJ';
            }
            
            if (profileAvatar) {
                profileAvatar.style.backgroundImage = '';
                profileAvatar.textContent = 'AJ';
            }
            
            localStorage.removeItem('profilePicture');
        });
    }
    
    const savedProfilePicture = localStorage.getItem('profilePicture');
    if (savedProfilePicture) {
        if (profilePreview) {
            profilePreview.style.backgroundImage = `url(${savedProfilePicture})`;
            profilePreview.textContent = '';
        }
        
        if (profileAvatar) {
            profileAvatar.style.backgroundImage = `url(${savedProfilePicture})`;
            profileAvatar.textContent = '';
        }
    }
    
    const editBtn = document.getElementById('editPersonalInfoBtn');
    const cancelBtn = document.getElementById('cancelEditBtn');
    const saveBtn = document.getElementById('savePersonalInfoBtn');
    const personalInfoForm = document.getElementById('personalInfoForm');
    const validationMessage = document.getElementById('validationMessage');
    
    if (editBtn && personalInfoForm) {
        editBtn.addEventListener('click', function() {
            personalInfoForm.style.display = 'block';
            this.style.display = 'none';
        });
    }
    
    if (cancelBtn && personalInfoForm && editBtn) {
        cancelBtn.addEventListener('click', function() {
            personalInfoForm.style.display = 'none';
            editBtn.style.display = 'block';
            if (validationMessage) validationMessage.style.display = 'none';
        });
    }
    
    if (saveBtn && validationMessage && editBtn) {
        saveBtn.addEventListener('click', function() {
            const fullName = document.getElementById('fullName').value;
            const cardName = document.getElementById('cardName').value;
            
            if (fullName !== cardName) {
                validationMessage.textContent = 'Error: Your name must match the name on your bank card for verification purposes.';
                validationMessage.className = 'validation-message validation-error';
                validationMessage.style.display = 'block';
                return;
            }
            
            document.getElementById('profileName').textContent = fullName;
            
            validationMessage.textContent = 'Profile updated successfully!';
            validationMessage.className = 'validation-message validation-success';
            validationMessage.style.display = 'block';
            
            setTimeout(() => {
                personalInfoForm.style.display = 'none';
                editBtn.style.display = 'block';
                validationMessage.style.display = 'none';
            }, 2000);
        });
    }
    
    const autoLockToggle = document.getElementById('autoLockToggle');
    const autoLockTimerSection = document.getElementById('autoLockTimerSection');
    const autoLockTimer = document.getElementById('autoLockTimer');
    
    if (autoLockToggle && autoLockTimerSection && autoLockTimer) {
        const savedAutoLock = localStorage.getItem('autoLock') !== null ? 
            localStorage.getItem('autoLock') === 'true' : true;
        const savedAutoLockTimer = localStorage.getItem('autoLockTimer') || '5';
            
        autoLockToggle.checked = savedAutoLock;
        autoLockTimer.value = savedAutoLockTimer;
        autoLockTimerSection.style.display = savedAutoLock ? 'flex' : 'none';
        
        autoLockToggle.addEventListener('change', function() {
            const isEnabled = this.checked;
            autoLockTimerSection.style.display = isEnabled ? 'flex' : 'none';
            localStorage.setItem('autoLock', isEnabled);
        });
        
        autoLockTimer.addEventListener('change', function() {
            localStorage.setItem('autoLockTimer', this.value);
        });
    }
    
    const twoFactorToggle = document.getElementById('twoFactorToggle');
    const emailNotificationsToggle = document.getElementById('emailNotificationsToggle');
    const pushNotificationsToggle = document.getElementById('pushNotificationsToggle');
    const transactionAlertsToggle = document.getElementById('transactionAlertsToggle');
    
    if (twoFactorToggle) {
        twoFactorToggle.checked = localStorage.getItem('twoFactor') === 'true';
        twoFactorToggle.addEventListener('change', function() {
            localStorage.setItem('twoFactor', this.checked);
        });
    }
    
    if (emailNotificationsToggle) {
        emailNotificationsToggle.checked = localStorage.getItem('emailNotifications') !== 'false';
        emailNotificationsToggle.addEventListener('change', function() {
            localStorage.setItem('emailNotifications', this.checked);
        });
    }
    
    if (pushNotificationsToggle) {
        pushNotificationsToggle.checked = localStorage.getItem('pushNotifications') !== 'false';
        pushNotificationsToggle.addEventListener('change', function() {
            localStorage.setItem('pushNotifications', this.checked);
        });
    }
    
    if (transactionAlertsToggle) {
        transactionAlertsToggle.checked = localStorage.getItem('transactionAlerts') !== 'false';
        transactionAlertsToggle.addEventListener('change', function() {
            localStorage.setItem('transactionAlerts', this.checked);
        });
    }
    
    function openLinkModal(accountType) {
        const modal = document.getElementById('linkModal');
        const title = document.getElementById('modalTitle');
        
        switch(accountType) {
            case 'bank':
                title.textContent = 'Link Bank Account';
                break;
            case 'credit':
                title.textContent = 'Link Credit Card';
                break;
            case 'loan':
                title.textContent = 'Link Loan Account';
                break;
            case 'investment':
                title.textContent = 'Link Investment Account';
                break;
        }
        
        modal.style.display = 'flex';
    }
    
    function closeModal() {
        document.getElementById('linkModal').style.display = 'none';
    }
    
    function linkAccount() {
        alert('Account linked successfully!');
        closeModal();
    }
    
    window.addEventListener('click', function(event) {
        const modal = document.getElementById('linkModal');
        if (event.target === modal) {
            closeModal();
        }
    });
    
    window.openLinkModal = openLinkModal;
    window.closeModal = closeModal;
    window.linkAccount = linkAccount;
});