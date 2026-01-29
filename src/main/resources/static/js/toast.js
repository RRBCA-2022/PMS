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


function openViewModal(purchaseId) {
    // Show Backdrop and Modal
    document.getElementById('viewPurchaseBackdrop').style.display = 'block';
    document.getElementById('viewPurchaseModal').style.display = 'block';

    // Fetch details via API
    fetch(`/purchases/${purchaseId}`)
        .then(response => {
            if (!response.ok) throw new Error("Network response was not ok");
            return response.json();
        })
        .then(data => {
            // Mapping fields to match your Java Entity (Purchase.java)
            document.getElementById('view-purchase-id').innerText = "#" + data.id;

            // Formatting the date (Assuming LocalDateTime string)
            const date = new Date(data.datetime);
            document.getElementById('view-date').innerText = date.toLocaleDateString();

            document.getElementById('view-supplier').innerText = data.supplierName;
            document.getElementById('view-total-items').innerText = data.items.length;
            const currency = document.getElementById('global-currency-symbol').value;
            document.getElementById('view-total-amount').innerText = currency +" "+ data.totalAmount.toFixed(2);
            // Medicine Names (Text format)
            const medList = document.getElementById('view-medicine-list');
            medList.innerHTML = '';

            // Mapping fields to match your Java Entity (PurchaseItem.java)
            data.items.forEach(item => {
                const itemDiv = document.createElement('div');
                itemDiv.className = 'list-group-item border-0 ps-0 pb-0';
                itemDiv.innerHTML = `
                    <div class="d-flex justify-content-between align-items-center">
                        <span>
                            <i class="bi bi-patch-check text-success me-2"></i>
                            <strong>${item.medicineName}</strong> 
                        </span>
                        <span class="text-muted">
                            ${item.qty} units @ ${currency +" "+ item.price.toFixed(2)}
                        </span>
                    </div>
                `;
                medList.appendChild(itemDiv);
            });
        })
        .catch(err => {
            console.error("Could not load purchase details:", err);
            alert("Error: Could not retrieve purchase data from server.");
        });
}

function closeViewModal() {
    document.getElementById('viewPurchaseBackdrop').style.display = 'none';
    document.getElementById('viewPurchaseModal').style.display = 'none';
}

