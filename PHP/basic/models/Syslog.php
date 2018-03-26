<?php

namespace app\models;

use Yii;

/**
 * This is the model class for table "syslog".
 *
 * @property integer $id
 * @property string $userid
 * @property string $beaconid
 * @property string $logtime
 * @property integer $triggertype
 */
class Syslog extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'syslog';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['logtime'], 'safe'],
            [['triggertype'], 'integer'],
            [['userid'], 'string', 'max' => 10],
            [['beaconid'], 'string', 'max' => 50]
        ];
    }

    /**
     * @inheritdoc
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'userid' => 'Userid',
            'beaconid' => 'Beaconid',
            'logtime' => 'Logtime',
            'triggertype' => 'Triggertype',
        ];
    }
}
