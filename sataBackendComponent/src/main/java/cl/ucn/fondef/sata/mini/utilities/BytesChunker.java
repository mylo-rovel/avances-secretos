package cl.ucn.fondef.sata.mini.utilities;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

/**
 * The type Bytes chunker.
 */
public final class BytesChunker {
    /**
     * Get bytes chunks array byte [ ] [ ].
     *
     * @param fileToChunk the file to chunk
     * @return the byte [ ] [ ]
     * @throws IOException the io exception
     */
// Queremos obtener un array de arrays (chunks de bytes) de maximo 4194304 bytes cada uno
    // El objetivo es evitar este error:
    // RESOURCE_EXHAUSTED: gRPC message exceeds maximum size 4194304: ...
    public static byte[][] getBytesChunksArray (MultipartFile fileToChunk) throws IOException {
        final byte[] fileBytes = fileToChunk.getBytes();
        final int grpcMaxFileSize = 4194304;
        final long fileSize = fileToChunk.getSize();

        long amountOfChunks = (fileSize/grpcMaxFileSize); // this give us the e
        byte[][] allChunksArr = new byte[(int)amountOfChunks][];

        for (int i = 0; i < amountOfChunks; i++) {
            int leftEdge = i*grpcMaxFileSize;
            int rightEdge = (i+1)*grpcMaxFileSize;

            if (i == amountOfChunks) {
                // Obtener el ultimo chunk pequeño => este grupo tiene un length variable
                rightEdge = fileBytes.length;
            }

            byte[] chunkArr = Arrays.copyOfRange(fileBytes, leftEdge, rightEdge);
            allChunksArr[i] = chunkArr;
        }
        System.out.println("allChunksArr.length = " + allChunksArr.length);
        return allChunksArr;
    }
}
