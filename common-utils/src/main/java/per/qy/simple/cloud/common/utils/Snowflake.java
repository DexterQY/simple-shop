package per.qy.simple.cloud.common.utils;

/**
 * Function: Snowflake
 * Description:
 * Author: QY
 * Date: 2019/6/17
 */
public class Snowflake {

    private final long twepoch;
    private final long workerIdBits;
    private final long datacenterIdBits;
    private final long maxWorkerId;
    private final long maxDatacenterId;
    private final long sequenceBits;
    private final long workerIdShift;
    private final long datacenterIdShift;
    private final long timestampLeftShift;
    private final long sequenceMask;
    private long workerId;
    private long dataCenterId;
    private long sequence;
    private long lastTimestamp;

    public long getWorkerId(long id) {
        return id >> 12 & 31L;
    }

    public long getDataCenterId(long id) {
        return id >> 17 & 31L;
    }

    public long getGenerateDateTime(long id) {
        return (id >> 22 & 2199023255551L) + 1288834974657L;
    }

    public Snowflake(long workerId, long dataCenterId) {
        this.twepoch = 1288834974657L;
        this.workerIdBits = 5L;
        this.datacenterIdBits = 5L;
        this.maxWorkerId = 31L;
        this.maxDatacenterId = 31L;
        this.sequenceBits = 12L;
        this.workerIdShift = 12L;
        this.datacenterIdShift = 17L;
        this.timestampLeftShift = 22L;
        this.sequenceMask = 4095L;
        this.sequence = 0L;
        this.lastTimestamp = -1L;
        if (workerId <= 31L && workerId >= 0L) {
            if (dataCenterId <= 31L && dataCenterId >= 0L) {
                this.workerId = workerId;
                this.dataCenterId = dataCenterId;
            } else {
                throw new IllegalArgumentException("datacenter Id can't be greater than 31 or less than 0");
            }
        } else {
            throw new IllegalArgumentException("worker Id can't be greater than 31 or less than 0");
        }
    }

    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();
        if (timestamp < this.lastTimestamp) {
            throw new IllegalStateException(String.format("Clock moved backwards. Refusing to generate id for %sms", this.lastTimestamp - timestamp));
        } else {
            if (this.lastTimestamp == timestamp) {
                this.sequence = this.sequence + 1L & 4095L;
                if (this.sequence == 0L) {
                    timestamp = this.tilNextMillis(this.lastTimestamp);
                }
            } else {
                this.sequence = 0L;
            }

            this.lastTimestamp = timestamp;
            return timestamp - 1288834974657L << 22 | this.dataCenterId << 17 | this.workerId << 12 | this.sequence;
        }
    }

    public String nextIdStr() {
        return Long.toString(this.nextId());
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp;
        for (timestamp = System.currentTimeMillis(); timestamp <= lastTimestamp; timestamp = System.currentTimeMillis()) {
        }
        return timestamp;
    }
}
