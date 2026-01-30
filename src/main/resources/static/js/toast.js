// Ensure toast container exists (creates it once)
function getToastContainer() {
    let container = document.getElementById('toast-container');
    if (!container) {
        container = document.createElement('div');
        container.id = 'toast-container';
        document.body.appendChild(container);
    }
    return container;
}

// Creates the toast div
function addToast(message, type = 'success') {
    const toast = document.createElement('div');
    toast.classList.add('toast');

    if(type === 'success') toast.classList.add('success');
    else if(type === 'error') toast.classList.add('error');
    else if(type === 'info') toast.classList.add('info');

    toast.innerHTML = message;

    const closeBtn = document.createElement('span');
    closeBtn.innerHTML = '&times;';
    closeBtn.classList.add('close-btn');
    closeBtn.onclick = () => toast.remove();
    toast.appendChild(closeBtn);

    return toast;
}

// Shows the toast
function showToast(message, type = 'success', duration = 3000) {
    const toastType = type || 'success';

    const container = getToastContainer();
    const toast = addToast(message, toastType);
    container.appendChild(toast);

    // Trigger show animation
    setTimeout(() => toast.classList.add('show'), 100);

    // Auto-remove after duration
    setTimeout(() => toast.remove(), duration);
}

