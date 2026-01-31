/* ===============================
   VIEW PURCHASE MODAL
=============================== */

function openViewModal(purchaseId) {
    // Show Backdrop and Modal
    openModal("viewPurchaseModal", "viewPurchaseBackdrop");

    // Fetch details via API
    fetch(`/purchases/${purchaseId}`)
        .then(response => {
            if (!response.ok) throw new Error("Purchase not found!");
            return response.json();
        })
        .then(data => {
            // Mapping fields to match your Java Entity (Purchase.java)
            document.getElementById('view-purchase-id').innerText = "#" + data.id;
            document.getElementById('view-order-status').innerText = data.orderStatus;
            // Formatting the date
            const date = new Date(data.timestamp);
            document.getElementById('view-date').innerText = date.toLocaleDateString();

            document.getElementById('view-supplier').innerText = data.supplierName;
            document.getElementById('view-reviewer').innerText = data.reviewerName || '-';
            const reviewdate = data.reviewTimestamp ? new Date(data.reviewTimestamp) : null;
            document.getElementById('view-review-date').innerText = reviewdate
                ? reviewdate.toLocaleDateString()
                : '-';

            document.getElementById('view-total-items').innerText = data.items.length.toString();
            const currencySymbol = document.getElementById('global-currency-symbol').value;
            document.getElementById('view-total-amount').innerText = currencySymbol +" "+ data.totalAmount.toFixed(2);
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
                            <i class="bi bi-capsule me-2"></i>
                            <strong>${item.medicineName}</strong> 
                        </span>
                        <span class="text-muted">
                            ${item.qty} units @ ${currencySymbol +" "+ item.price.toFixed(2)}
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
    closeModal("viewPurchaseModal", "viewPurchaseBackdrop");
}
