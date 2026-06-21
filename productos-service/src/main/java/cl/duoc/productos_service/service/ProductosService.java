package cl.duoc.productos_service.service;

import cl.duoc.productos_service.dto.ProductosDTO;
import cl.duoc.productos_service.exception.ProductosNoEncontradoException;
import cl.duoc.productos_service.mapper.ProductoMapper;
import cl.duoc.productos_service.modelo.Productos;
import cl.duoc.productos_service.repository.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductosService {
    @Autowired
    private ProductosRepository productosRepository;

    @Autowired
    private ProductoMapper productoMapper;


    public List<Productos> findAll(){
        return productosRepository.findAll();
    }


    public ProductosDTO findById(Long id){

        Productos productos = productosRepository.findById(id).orElseThrow(() -> new ProductosNoEncontradoException("No existe un producto con id: " + id));

        return productoMapper.toDTO(productos);
    }



    public Productos save(Productos productos){
        return productosRepository.save(productos);
    }

    public void delete(Long id){
        productosRepository.deleteById(id);
    }

    public Productos update(Long id,Productos productos){
        Productos productosActualizado = productosRepository.findById(id).orElse(null);
        if (productosActualizado == null) return null;
        productosActualizado.setNombre(productos.getNombre());
        productosActualizado.setCategoria(productos.getCategoria());
        productosActualizado.setPrecio(productos.getPrecio());
        productosActualizado.setDisponible(productos.getDisponible());
        productosActualizado.setDescripcion(productos.getDescripcion());
        return productosRepository.save(productosActualizado);


    }
    public List<ProductosDTO> findDTOList(){return productoMapper.toDTOlist(findAll());}
}
