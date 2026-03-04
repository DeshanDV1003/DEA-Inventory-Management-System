/**
 * DeletePurchaseOrder Page
 *
 * Purpose:
 * - Provides a "Are you sure?" screen before a destructive database operation.
 * - Displays order data in a disabled (read-only) state so the user knows
 *   exactly what they are deleting.
 */
import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { purchaseOrderService } from '../../services/purchaseOrderService';
import Sidebar from "../../components/Sidebar.jsx";

const DeletePurchaseOrder = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [order, setOrder] = useState(null);

    useEffect(() => {
        purchaseOrderService.getById(id).then(res => setOrder(res.data));
    }, [id]);

    const handleConfirmDelete = async () => {
        try {
            await purchaseOrderService.delete(id);
            navigate('/purchase-orders');
        } catch (e) { alert("Delete failed"); }
    };

    if (!order) return <div className="loader"></div>;

    return (
        <div className="products-root">
            <Sidebar />
        <div className="modal-overlay">
            <div className="modal confirm-modal">
                <h2 style={{ color: '#f87171' }}>Delete Purchase Order?</h2>
                <p>This action is permanent. All items in PO #{order.poNumber} will be lost.</p>

                <div className="modal-form" style={{ opacity: 0.5, pointerEvents: 'none', marginBottom: '1.5rem' }}>
                    <div className="field-group">
                        <label>PO NUMBER</label>
                        <input type="text" value={order.poNumber} readOnly />
                    </div>
                    <div className="field-group">
                        <label>WAREHOUSE</label>
                        <input type="text" value={order.warehouseId} readOnly />
                    </div>
                </div>

                <div className="modal-actions">
                    <button className="cancel-btn" onClick={() => navigate('/purchase-orders')}>Go Back</button>
                    <button className="delete-confirm-btn" onClick={handleConfirmDelete}>Confirm Delete</button>
                </div>
            </div>
        </div>
        </div>
    );
};

export default DeletePurchaseOrder;