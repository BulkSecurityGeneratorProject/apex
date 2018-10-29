package br.com.jhisolution.apex.web.rest;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sun.imageio.plugins.gif.GIFImageReader;
import com.sun.imageio.plugins.gif.GIFImageReaderSpi;

import br.com.jhisolution.apex.repository.CadastroRepository;

/**
 * REST controller for managing Cadastro.
 */
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/image")
public class CadastroImageResource {

    private final Logger log = LoggerFactory.getLogger(CadastroImageResource.class);

    private final CadastroRepository cadastroRepository;

    public CadastroImageResource(CadastroRepository cadastroRepository) {
        this.cadastroRepository = cadastroRepository;
    }
	
	/**
     * GET  /cadastros/gif/:id : get the "id" cadastro.
     *
     * @param id the id of the cadastro to retrieve
     * @return jpg
     */
    @GetMapping("/cadastro/gif/{id}")
    public byte[] getGifCadastro(@PathVariable Long id) {
        log.debug("REST request to get GIF from Cadastro by id : {}", id);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_GIF);
        byte[] gif = cadastroRepository.findGifByIdCadastro(id);
        return gif;
    }

	/**
     * GET  /cadastros/gif/:id : get the "id" cadastro.
     *
     * @param id the id of the cadastro to retrieve
     * @return jpg
     */
    @GetMapping("/cadastro/gif/link/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> getGifCadastroLink(@PathVariable Long id) {
        log.debug("REST request to get GIF from Cadastro by id : {}", id);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_GIF);
        byte[] gif = cadastroRepository.findGifByIdCadastro(id);
        return new ResponseEntity<byte[]>(gif, headers,	HttpStatus.OK);
    }
	
	/**
     * GET  /cadastros/gif/:id : get the "id" cadastro.
     *
     * @param id the id of the cadastro to retrieve
     * @return jpg
     */
    @GetMapping("/cadastro/gif/link/thumb/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> getGifCadastroLinkThumb(@PathVariable Long id) {
        log.debug("REST request to get GIF from Cadastro by id : {}", id);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_GIF);
        byte[] gif = cadastroRepository.findGifByIdCadastro(id);
		
		byte[] thumb = getImage(gif);
	 
        return new ResponseEntity<byte[]>(thumb, headers,	HttpStatus.OK);
    }
	
	private byte[] getImage(byte[] imageByte) {
		
		try{
			
			BufferedImage image = getFrame(imageByte);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write( image, "jpg", baos );
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			
			return imageInByte;
			
		} catch(IOException e){
			e.printStackTrace();
		}	
		
		return null;
	}

	private BufferedImage getFrame(byte[] imageByte) throws IOException {
	    
		List<BufferedImage> lista = getFrames(imageByte);
		
		lista.get(1);
		
	    return lista.get(1);
	}

	private List<BufferedImage> getFrames(byte[] imageByte) throws IOException {
	    
		InputStream in = new ByteArrayInputStream(imageByte);
		BufferedImage bImage = ImageIO.read(in);
			
		List<BufferedImage> frames = new ArrayList<BufferedImage>();
	    BufferedImage master = new BufferedImage(bImage.getWidth(), bImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

	    ByteArrayInputStream bais = new ByteArrayInputStream(imageByte);
        ImageInputStream iis = ImageIO.createImageInputStream(bais);
        
	    ImageReader ir = new GIFImageReader(new GIFImageReaderSpi());
	    ir.setInput(iis);
	    for (int i = 0; i < ir.getNumImages(true); i++) {
	    	// frames.add(ir.read(i));
	        frames.add(new BufferedImage(bImage.getWidth(), bImage.getHeight(), BufferedImage.TYPE_INT_ARGB));
	        master.getGraphics().drawImage(ir.read(i), 0, 0, null);
	        frames.get(i).setData(master.getData());
	    }
	    return frames;
	}
	
	private static List<BufferedImage> getFrames(File gif) throws IOException {
	    
		List<BufferedImage> frames = new ArrayList<BufferedImage>();
	    BufferedImage master = new BufferedImage(1024, 1024, BufferedImage.TYPE_INT_ARGB);

	    ImageReader ir = new GIFImageReader(new GIFImageReaderSpi());
	    ir.setInput(ImageIO.createImageInputStream(gif));
	    for (int i = 0; i < ir.getNumImages(true); i++) {
	    	// frames.add(ir.read(i));
	        frames.add(new BufferedImage(1024, 1024, BufferedImage.TYPE_INT_ARGB));
	        master.getGraphics().drawImage(ir.read(i), 0, 0, null);
	        frames.get(i).setData(master.getData());
	    }
	    return frames;
	}
}
