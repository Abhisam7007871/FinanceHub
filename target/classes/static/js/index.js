// Basic sidebar functionality from reports.js
document.addEventListener('DOMContentLoaded', function() {
    // Logo click functionality
    const logo = document.querySelector('.logo');
    if (logo) {
        logo.style.cursor = 'pointer';
        logo.addEventListener('click', function() {
            window.location.href = '/';
        });
    }
});