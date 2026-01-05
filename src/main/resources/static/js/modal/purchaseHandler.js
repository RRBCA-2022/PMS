/* ===============================
   PURCHASE MODAL
=============================== */

let purchaseItems = [];

function openPurchaseModal() {
    openModal("purchaseModal", "purchaseBackdrop");

    // Reset items array
    purchaseItems = [];
    renderPurchaseItems();
}

function closePurchaseModal() {
    closeModal("purchaseModal", "purchaseBackdrop");

    // Reset items array
    purchaseItems = [];
    renderPurchaseItems();
}

/* ---------- Add / Render Medicines ---------- */
function addMedicineToPurchase(button) {
    const itemEl = button.closest(".list-group-item");
    const name = itemEl.querySelector("strong").innerText;
    const costText = itemEl.querySelector("small").innerText;
    const price = parseFloat(costText.replace('Cost: $', ''));

    const existing = purchaseItems.find(i => i.name === name);
    if (existing) {
        existing.qty += 1;
        existing.total = existing.qty * existing.price;
    } else {
        purchaseItems.push({ name, price, qty: 1, total: price });
    }

    renderPurchaseItems();
}

function renderPurchaseItems() {
    const list = document.getElementById("purchaseItemsList");
    list.innerHTML = "";

    let grandTotal = 0;

    purchaseItems.forEach((item, index) => {
        grandTotal += item.total;

        const div = document.createElement("div");
        div.className = "list-group-item d-flex justify-content-between align-items-center";

        div.innerHTML = `
            <div>
                <strong>${item.name}</strong><br>
                <small>$${item.price.toFixed(2)} each</small>
            </div>
            <div class="d-flex align-items-center gap-2">
                <button type="button" class="btn btn-sm btn-light" onclick="decreaseQty(${index})">−</button>
                <span>${item.qty}</span>
                <button type="button" class="btn btn-sm btn-light" onclick="increaseQty(${index})">+</button>
                <strong>$${item.total.toFixed(2)}</strong>
                <input type="hidden" name="items[${index}].name" value="${item.name}">
                <input type="hidden" name="items[${index}].price" value="${item.price}">
                <input type="hidden" name="items[${index}].qty" value="${item.qty}">
            </div>
        `;

        list.appendChild(div);
    });

    const grandTotalEl = document.getElementById("grandTotal");
    const currency = grandTotalEl.dataset.currSymbol || "$";
    grandTotalEl.innerText = currency + " " + grandTotal.toFixed(2);

}

/* ---------- Quantity Control ---------- */
function increaseQty(index) {
    purchaseItems[index].qty += 1;
    purchaseItems[index].total = purchaseItems[index].qty * purchaseItems[index].price;
    renderPurchaseItems();
}

function decreaseQty(index) {
    purchaseItems[index].qty -= 1;
    if (purchaseItems[index].qty <= 0) {
        purchaseItems.splice(index, 1);
    } else {
        purchaseItems[index].total = purchaseItems[index].qty * purchaseItems[index].price;
    }
    renderPurchaseItems();
}

/* ---------- Filter Available Medicines ---------- */
function filterMedicines() {
    const search = document.getElementById("medicineSearch").value.toLowerCase();
    document.querySelectorAll("#availableProducts .list-group-item").forEach(item => {
        const name = item.querySelector("strong").innerText.toLowerCase();
        item.style.display = name.includes(search) ? "" : "none";
    });
}
