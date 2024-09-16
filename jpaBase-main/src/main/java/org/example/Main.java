package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");

        EntityManager em = emf.createEntityManager();
        System.out.println("en marcha Alberto");

        try {
            // Persistir una nueva entidad Person
            em.getTransaction().begin();

            Factura factura1 = new Factura();
            factura1.setNumero(12);
            factura1.setFecha("06/09/2024");

            Domicilio dom = new Domicilio("San Martin", 1224);
            Cliente cliente = new Cliente("Pablo", "Muñoz", 15458962);
            cliente.setDomicilio(dom);
            dom.setCliente(cliente);

            factura1.setCliente(cliente);

            Categoria perecederos = new Categoria("Perecederos");
            Categoria lacteos = new Categoria("Lacteos");
            Categoria limpieza = new Categoria("Limpieza");

            Articulo art1 = new Articulo(200, "Yogurt Ser sabor vainilla", 20);
            Articulo art2 = new Articulo(300, "Detergente Limon", 80);

            art1.getCategorias().add(perecederos);
            art1.getCategorias().add(lacteos);
            lacteos.getArticulos().add(art1);
            perecederos.getArticulos().add(art1);

            art2.getCategorias().add(limpieza);
            limpieza.getArticulos().add(art2);

            DetalleFactura det1 = new DetalleFactura();

            det1.setArticulo(art1);
            det1.setCantidad(2);;
            det1.setSubtotal(40);

            art1.getDetallefacturas().add(det1);
            factura1.getDetalles().add(det1);
            det1.setFactura(factura1);

            DetalleFactura det2 = new DetalleFactura();
            det2.setArticulo(art2);
            det2.setCantidad(1);
            det2.setSubtotal(80);

            art2.getDetallefacturas().add(det2);
            factura1.getDetalles().add(det2);
            det2.setFactura(factura1);

            factura1.setTotal(120);

            em.persist(factura1);

            em.flush();

            em.getTransaction().commit();


        }catch (Exception e){
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
            System.out.println("No se pudo grabar la clase Persona");
        }

        // Cerrar el EntityManager y el EntityManagerFactory
        em.close();
        emf.close();
    }
}
