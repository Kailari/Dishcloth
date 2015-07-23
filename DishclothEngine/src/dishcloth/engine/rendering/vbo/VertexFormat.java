package dishcloth.engine.rendering.vbo;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

/**
 * VertexFormat.java
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 22.7.2015
 */

public class VertexFormat {

	private final List<VertexAttribute> attributes;

	public VertexFormat(VertexAttribute... attributes) {
		this.attributes = new ArrayList<>();
		for (int i = 0; i < attributes.length; i++) {
			this.attributes.add( attributes[i] );
		}
	}

	public static void apply(VertexFormat format) {
		int arrayIndex = 0;
		for (VertexAttribute attribute : format.attributes) {

			glEnableVertexAttribArray( arrayIndex );
			glVertexAttribPointer( arrayIndex,
			                       attribute.getSize(),
			                       attribute.getGLType(),
			                       attribute.getNormalize(),
			                       attribute.getStride(),
			                       attribute.getOffset() );
			arrayIndex++;
		}
	}

	public static class VertexAttribute {
		private final int size;
		private final int gl_type;
		private final int stride;
		private final int offset;
		private final boolean normalize;

		public VertexAttribute(int size, int gl_type, int stride, int offset, boolean normalize) {
			this.size = size;
			this.gl_type = gl_type;
			this.stride = stride;
			this.offset = offset;
			this.normalize = normalize;
		}

		public int getSize() {
			return size;
		}

		public int getGLType() {
			return gl_type;
		}

		public int getStride() {
			return stride;
		}

		public int getOffset() {
			return offset;
		}

		public boolean getNormalize() {
			return normalize;
		}
	}
}
