import { useEffect, useRef } from "react";
import { Html5Qrcode } from "html5-qrcode";

const BarcodeScanner = ({ onScan, onClose }) => {
  const scannerRef = useRef(null);

  useEffect(() => {
    const scanner = new Html5Qrcode("barcode-reader");
    scannerRef.current = scanner;

    scanner
      .start(
        { facingMode: "environment" },
        { fps: 10, qrbox: { width: 300, height: 150 } },
        (decodedText) => {
          scanner.stop().then(() => {
            onScan(decodedText);
          });
        }
      )
      .catch((err) => {
        console.error("Camera error:", err);
      });

    return () => {
      if (scannerRef.current?.isScanning) {
        scannerRef.current.stop().catch(() => {});
      }
    };
  }, [onScan]);

  return (
    <div className="scanner-overlay">
      <div className="scanner-container">
        <div className="scanner-header">
          <h3>Scan Barcode</h3>
          <button className="modal-close" onClick={onClose}>
            ✕
          </button>
        </div>
        <div id="barcode-reader" />
        <p className="scanner-hint">Point your camera at a barcode</p>
      </div>
    </div>
  );
};

export default BarcodeScanner;
