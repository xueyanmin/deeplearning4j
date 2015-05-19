package org.nd4j.linalg.jcublas.buffer.allocation;

import jcuda.Pointer;
import jcuda.runtime.JCuda;
import jcuda.runtime.cudaMemcpyKind;
import org.nd4j.linalg.api.buffer.DataBuffer;
import org.nd4j.linalg.api.buffer.allocation.MemoryStrategy;
import org.nd4j.linalg.jcublas.buffer.BaseCudaDataBuffer;
import org.nd4j.linalg.jcublas.buffer.JCudaBuffer;
import org.nd4j.linalg.jcublas.context.ContextHolder;

import java.util.Map;

/**
 * @author Adam Gibson
 */
public class PinnedMemoryStrategy implements MemoryStrategy {
    @Override
    public Object copyToHost(DataBuffer copy) {
        JCudaBuffer buf2 = (JCudaBuffer) copy;
        Map<String,BaseCudaDataBuffer.DevicePointerInfo> pointersToContexts = buf2.getPointersToContexts();
        BaseCudaDataBuffer.DevicePointerInfo devicePointerInfo = pointersToContexts.get(Thread.currentThread().getName());
        BaseCudaDataBuffer.checkResult(
                JCuda.cudaMemcpyAsync(
                        buf2.getHostPointer()
                        , devicePointerInfo.getPointer()
                        , devicePointerInfo.getLength()
                        , cudaMemcpyKind.cudaMemcpyDeviceToHost
                        , ContextHolder.getInstance().getCudaStream()));


        return buf2.getHostPointer();
    }

    @Override
    public Object alloc(DataBuffer buffer) {
        Pointer hostPointer = new Pointer();
        BaseCudaDataBuffer.DevicePointerInfo devicePointerInfo = new BaseCudaDataBuffer.DevicePointerInfo(hostPointer, buffer.length());
        BaseCudaDataBuffer.checkResult(JCuda.cudaHostAlloc(hostPointer, buffer.getElementSize() * buffer.length(), JCuda.cudaHostAllocPortable));
        return devicePointerInfo;
    }

    @Override
    public void free(DataBuffer buffer) {
        JCudaBuffer buf2 = (JCudaBuffer) buffer;
        Map<String,BaseCudaDataBuffer.DevicePointerInfo> pointers = buf2.getPointersToContexts();
        BaseCudaDataBuffer.DevicePointerInfo devicePointerInfo = pointers.get(Thread.currentThread().getName());
        BaseCudaDataBuffer.checkResult(JCuda.cudaFreeHost(devicePointerInfo.getPointer()));


    }
}
