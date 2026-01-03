function showToast(message, type = 'success', duration = 3000) {
    const container = document.getElementById('toast-container');

    // Create toast div
    const toast = document.createElement('div');
    toast.classList.add('toast');
    if(type === 'success') toast.classList.add('success');
    if(type === 'error') toast.classList.add('error');
    if(type === 'info') toast.classList.add('info');
    toast.innerHTML = message;

    // Add close button
    const closeBtn = document.createElement('span');
    closeBtn.innerHTML = '&times;';
    closeBtn.classList.add('close-btn');
    closeBtn.onclick = () => toast.remove();
    toast.appendChild(closeBtn);

    // Add to container
    container.appendChild(toast);

    // Show with animation
    setTimeout(() => toast.classList.add('show'), 100);

    // Auto remove after duration
    setTimeout(() => toast.remove(), duration);
}