<?php

namespace app\models;

use Yii;

/**
 * This is the model class for table "key".
 *
 * @property integer $id
 * @property string $gentime
 * @property boolean $isactive
 */
class Key extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'key';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['gentime'], 'safe'],
            [['isactive'], 'boolean']
        ];
    }

    /**
     * @inheritdoc
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'gentime' => 'Gentime',
            'isactive' => 'Isactive',
        ];
    }
}
