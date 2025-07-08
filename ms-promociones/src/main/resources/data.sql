-- Datos de prueba para ms-promociones
-- Promociones activas y realistas para HomeFixer

-- Promociones generales
INSERT INTO promociones (titulo, descripcion, porcentaje_descuento, monto_descuento, tipo, fecha_inicio, fecha_fin, especialidad_aplicable, activa, usos_maximos, usos_actuales) VALUES
('Descuento Primera Compra', '20% de descuento en tu primer servicio técnico', 20.00, NULL, 'PRIMERA_COMPRA', '2024-01-01', '2024-12-31', NULL, true, 1000, 45),
('Cliente Premium', '25% de descuento para clientes premium', 25.00, NULL, 'CLIENTE_PREMIUM', '2024-01-01', '2024-12-31', NULL, true, 500, 120),
('Descuento Fijo $5000', '$5000 de descuento en cualquier servicio', NULL, 5000.00, 'MONTO_FIJO', '2024-01-01', '2024-06-30', NULL, true, 200, 89);

-- Promociones por especialidad
INSERT INTO promociones (titulo, descripcion, porcentaje_descuento, monto_descuento, tipo, fecha_inicio, fecha_fin, especialidad_aplicable, activa, usos_maximos, usos_actuales) VALUES
('Promo Refrigeración', '15% off en servicios de refrigeración', 15.00, NULL, 'PORCENTAJE', '2024-01-01', '2024-06-30', 'Refrigeración', true, 300, 87),
('Descuento Lavadoras', '18% de descuento en reparación de lavadoras', 18.00, NULL, 'PORCENTAJE', '2024-02-01', '2024-07-31', 'Electrodomésticos', true, 250, 156),
('Promo Calefont', '$8000 de descuento en reparación de calefont', NULL, 8000.00, 'MONTO_FIJO', '2024-01-15', '2024-05-15', 'Gasfitería', true, 150, 67),
('Aire Acondicionado', '12% off en mantención de aires acondicionados', 12.00, NULL, 'PORCENTAJE', '2024-03-01', '2024-08-31', 'Climatización', true, 400, 203);

-- Promociones estacionales
INSERT INTO promociones (titulo, descripcion, porcentaje_descuento, monto_descuento, tipo, fecha_inicio, fecha_fin, especialidad_aplicable, activa, usos_maximos, usos_actuales) VALUES
('Promo Invierno 2024', '20% de descuento en calefacción para el invierno', 20.00, NULL, 'PORCENTAJE', '2024-05-01', '2024-09-30', 'Calefacción', true, 500, 234),
('Verano Sin Problemas', '15% off en reparación de ventiladores y A/C', 15.00, NULL, 'PORCENTAJE', '2024-11-01', '2025-03-31', 'Climatización', true, 600, 123);

-- Promociones vencidas (para testing)
INSERT INTO promociones (titulo, descripcion, porcentaje_descuento, monto_descuento, tipo, fecha_inicio, fecha_fin, especialidad_aplicable, activa, usos_maximos, usos_actuales) VALUES
('Promo Navidad 2023', '30% de descuento navideño', 30.00, NULL, 'PORCENTAJE', '2023-12-01', '2023-12-31', NULL, false, 100, 100),
('Black Friday Técnicos', '40% de descuento en Black Friday', 40.00, NULL, 'PORCENTAJE', '2023-11-24', '2023-11-27', NULL, false, 50, 47);

-- Promociones con pocos usos restantes
INSERT INTO promociones (titulo, descripcion, porcentaje_descuento, monto_descuento, tipo, fecha_inicio, fecha_fin, especialidad_aplicable, activa, usos_maximos, usos_actuales) VALUES
('Oferta Limitada', '35% de descuento - últimas 5 oportunidades', 35.00, NULL, 'PORCENTAJE', '2024-01-01', '2024-12-31', 'Electrónica', true, 50, 45),
('Super Descuento Final', '$15000 de descuento - solo 3 usos restantes', NULL, 15000.00, 'MONTO_FIJO', '2024-01-01', '2024-12-31', 'Electrodomésticos', true, 10, 7);