/* ===============================
   MODAL UTILS
=============================== */

/**
 * Open a modal by ID and optionally reset a form inside it
 * @param {string} modalId
 * @param {string} backdropId
 * @param {boolean} resetForm
 */
function openModal(modalId, backdropId, resetForm = true) {
    const modal = document.getElementById(modalId);
    const backdrop = document.getElementById(backdropId);

    if (!modal || !backdrop) return;

    modal.style.display = "block";
    backdrop.style.display = "block";
    document.body.style.overflow = "hidden";

    if (resetForm) {
        const form = modal.querySelector("form");
        if (form) form.reset();
    }
}

/**
 * Close a modal by ID and optionally reset a form inside it
 * @param {string} modalId
 * @param {string} backdropId
 * @param {boolean} resetForm
 */
function closeModal(modalId, backdropId, resetForm = true) {
    const modal = document.getElementById(modalId);
    const backdrop = document.getElementById(backdropId);

    if (!modal || !backdrop) return;

    modal.style.display = "none";
    backdrop.style.display = "none";
    document.body.style.overflow = "";

    if (resetForm) {
        const form = modal.querySelector("form");
        if (form) form.reset();
    }
}

function lockModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) modal.classList.add("modal-locked");
}

function unlockModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) modal.classList.remove("modal-locked");
}
